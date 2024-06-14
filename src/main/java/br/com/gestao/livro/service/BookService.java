package br.com.gestao.livro.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.gestao.livro.model.Book;
import br.com.gestao.livro.repository.BookRepository;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class BookService {
	
	@Autowired
	private BookRepository repository;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private S3Client s3Client;
	
	@Autowired
	public BookService(AwsSecretsManagerService awsSecretsManagerService) {
		AwsBasicCredentials awsCreds = awsSecretsManagerService.getAwsCredentials("your-secrets-name");
		s3Client = S3Client.builder()
				.region(Region.US_EAST_1)
				.credentialsProvider(StaticCredentialsProvider.create(awsCreds))
				.build();
	}
	
	public Book addBook(String title, String author, MultipartFile coverImage) throws IOException {
		UUID bookId = UUID.randomUUID();
		String coverImageUrl = uploadCoverImageToS3(bookId, coverImage);
		
		Book book = new Book();
		book.setId(bookId);
		book.setTitle(title);
		book.setAuthor(author);
		book.setCoverImageUrl(coverImageUrl);
		
		repository.save(book);
		
		kafkaTemplate.send("books", "Book added: " + title);
		
		return book;
	}
	
	public List<Book> getAllBooks() {
		return repository.findAll();
	}
	
	public void deleteBook(UUID id) {
		repository.deleteById(id);
		kafkaTemplate.send("books", "Book deleted: " + id);
	}
	
	private String uploadCoverImageToS3(UUID bookId, MultipartFile coverImage) throws IOException {
		String bucketName = "bucket-adm-1";
		String key = "covers/" + bookId + ".jpg";
		
		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
				.bucket(bucketName)
				.key(key)
				.build();
		
		try {
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(coverImage.getInputStream(), coverImage.getSize()));
        } catch (S3Exception e) {
            throw new IOException("Failed to upload cover image to S3", e);
        }
		
		return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key)).toExternalForm();
	}

}
