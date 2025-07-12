package com.DriverMileageTracker.Backend.Repository;

import com.DriverMileageTracker.Backend.Database.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByVehicleNumber(String vehicleNumber);

    Users findByPhoneNumberAndVehicleNumberAndPassword(String phoneNumber,String vehicleNumber,String password);
    Users findByPhoneNumber(String phoneNumber);

    Users findByVehicleNumber(String vehicleNumber);

    Optional<Users> findByPhoneNumberAndVehicleNumber(String phoneNumber, String vehicleNumber);

    boolean existsByPhoneNumberOrVehicleNumber(String phoneNumber,String vehicleNumber);
}
