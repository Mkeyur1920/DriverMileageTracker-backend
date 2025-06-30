package com.DriverMileageTracker.Backend.Controller;

import com.DriverMileageTracker.Backend.DTO.RoleDTO;
import com.DriverMileageTracker.Backend.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<RoleDTO> getAll() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public RoleDTO getById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @PostMapping("/save")
    public RoleDTO create(@RequestBody RoleDTO dto) {
        return roleService.createRole(dto);
    }

    @PutMapping("/{id}")
    public RoleDTO update(@PathVariable Long id, @RequestBody RoleDTO dto) {
        return roleService.updateRole(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        roleService.deleteRole(id);
    }

}
