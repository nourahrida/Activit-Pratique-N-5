package net.enset.etudiantspayementang;

import net.enset.etudiantspayementang.entites.Payment;
import net.enset.etudiantspayementang.entites.PaymentStatus;
import net.enset.etudiantspayementang.entites.PaymentType;
import net.enset.etudiantspayementang.entites.Student;
import net.enset.etudiantspayementang.repository.PaymentRepository;
import net.enset.etudiantspayementang.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class EtudiantsPayementAngApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtudiantsPayementAngApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                        PaymentRepository paymentRepository){
        return  args -> {

            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                            .firstName("SARA").lastName("Charkaoui").code("112").programId("BDCC")
                    .build());

            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("Noura").lastName("Hrida").code("113").programId("BDCC")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("Ayoub").lastName("Hajouj").code("114").programId("SDIA")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("YAssmine").lastName("thab").code("115").programId("SDIA")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("ALI").lastName("YASSINE").code("111").programId("BDCC")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("ZAhra").lastName("outalib").code("116").programId("GLSID")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("Abdo").lastName("ALxsander").code("117").programId("BDCC")
                    .build());

            PaymentType [] paymentTypes = PaymentType.values();
            Random random = new Random();
            studentRepository.findAll().forEach(st ->{
                for (int i=0 ; i<10 ; i++) {
                    int index =random.nextInt(paymentTypes.length);
                    Payment payment = Payment.builder()
                            .amount(100+(int)(Math.random()*20000))
                            .type(paymentTypes[index])
                            .status(PaymentStatus.CREATED)
                            .date(LocalDate.now())
                            .student(st)
                            .build();
                    paymentRepository.save(payment);
                }
            });


        };
    }

}
