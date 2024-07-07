package net.enset.etudiantspayementang.services;
import net.enset.etudiantspayementang.entites.Payment;
import net.enset.etudiantspayementang.entites.PaymentStatus;
import net.enset.etudiantspayementang.entites.PaymentType;
import net.enset.etudiantspayementang.entites.Student;
import net.enset.etudiantspayementang.repository.PaymentRepository;
import net.enset.etudiantspayementang.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    private PaymentRepository paymentRepository;
    private StudentRepository studentRepository;

    public PaymentService(PaymentRepository paymentRepository, StudentRepository studentRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
    }

    public Payment savePayment(MultipartFile Myfile, LocalDate date,
                               double amount, PaymentType type, String studentCode) throws IOException {


        Path forlderPath = Paths.get(System.getProperty("user.home"),"eset-data","payments");
        if(!Files.exists(forlderPath))
        {
            Files.createDirectories(forlderPath);
        }
        String fileName = UUID.randomUUID().toString();

        Path filePath = Paths.get(System.getProperty("user.home"),"eset-data","payments",fileName+".pdf");

        Files.copy(Myfile.getInputStream(),filePath);

        Student s = studentRepository.findByCode(studentCode);
        Payment payment = Payment.builder()
                .student(s)
                .date(date)
                .amount(amount)
                .type(type)
                .file(filePath.toUri().toString())
                .status(PaymentStatus.CREATED)
                .build();
        return paymentRepository.save(payment);
    }

    public Payment updatePaymentStatus(PaymentStatus status, @PathVariable Long id)
    {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    public byte[] getPaymentFile (Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }


}
