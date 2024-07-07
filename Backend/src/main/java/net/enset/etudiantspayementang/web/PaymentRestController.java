package net.enset.etudiantspayementang.web;

import lombok.Data;
import net.enset.etudiantspayementang.entites.Payment;
import net.enset.etudiantspayementang.entites.PaymentStatus;
import net.enset.etudiantspayementang.entites.PaymentType;
import net.enset.etudiantspayementang.entites.Student;
import net.enset.etudiantspayementang.repository.PaymentRepository;
import net.enset.etudiantspayementang.repository.StudentRepository;
import net.enset.etudiantspayementang.services.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import java.time.LocalDate;
import java.util.List;


@RestController
@CrossOrigin("*")
public class PaymentRestController {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;

    private PaymentService paymentService;


    public PaymentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository, PaymentService paymentService) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
    }

    @GetMapping(path="/payments")
    public List<Payment> allPayment(){
        return paymentRepository.findAll();
    }

    @GetMapping(path="/payments/{id}")
    public Payment paymentById(@PathVariable  Long id){
        return paymentRepository.findById(id).get();
    }

    @GetMapping(path = "/paymentStudents/{codeStudents}")
    public List<Payment> paymentByStudents(@PathVariable String codeStudents){
        return paymentRepository.findByStudentCode(codeStudents);
    }

    @GetMapping(path = "/paymentStatus/{Status}")
    public List<Payment> paymentByStatus(@PathVariable PaymentStatus Status){
        return paymentRepository.findByStatus(Status);
    }

    @GetMapping(path = "/paymentsType/{Type}")
    public List<Payment> paymentByType(@PathVariable PaymentType Type){
        return paymentRepository.findByType(Type);
    }

    @GetMapping("/students")
    public List<Student> allStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("/students/{code}")
    public Student StudentsByCode(@PathVariable String code){
        return studentRepository.findByCode(code);
    }

    @GetMapping("/students/{programId}/Program")
    public List<Student> StudentsByProgram(@PathVariable String programId){
        return studentRepository.findByProgramId(programId);
    }


    @PutMapping("/payments/{id}")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus status, @PathVariable Long id)
    {
        return this.paymentService.updatePaymentStatus(status,id);
    }

    @PostMapping(path = "/payments",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  Payment savePayment(@RequestParam  MultipartFile Myfile,LocalDate date,
                                double amount,PaymentType type,String studentCode) throws IOException {

      return this.paymentService.savePayment(Myfile ,date,amount,type,studentCode);
    }

    @GetMapping(path = "paymentFile/{paymentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile (@PathVariable Long paymentId) throws IOException {

        return this.paymentService.getPaymentFile(paymentId);
    }





}
