package net.enset.etudiantspayementang.repository;

import net.enset.etudiantspayementang.entites.Payment;
import net.enset.etudiantspayementang.entites.PaymentStatus;
import net.enset.etudiantspayementang.entites.PaymentType;
import net.enset.etudiantspayementang.entites.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    List<Payment> findByStudentCode(String code);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByType(PaymentType type);



}
