package com.DriverMileageTracker.Backend.Controller;

import com.DriverMileageTracker.Backend.DTO.UserDTO;
import com.DriverMileageTracker.Backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<UserDTO> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/save")
    public UserDTO create(@RequestBody UserDTO dto) {
        return userService.createUser(dto);
    }

    @PutMapping(value = "/update/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserDTO updateUser(
            @PathVariable Long userId,
            @RequestParam("name") String name,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("vehicleNumber") String vehicleNumber,
            @RequestParam(value = "password", required = false) String password,
            @RequestPart(value = "signature", required = false) MultipartFile signature,
            @RequestPart(value = "userPhoto", required = false) MultipartFile userPhoto
    ) throws IOException {
        return userService.updateUserProfile(userId, name, phoneNumber, vehicleNumber, password, signature != null ? signature.getBytes() : null,
                userPhoto != null ? userPhoto.getBytes() : null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
