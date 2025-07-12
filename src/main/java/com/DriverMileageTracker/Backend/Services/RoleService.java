package com.DriverMileageTracker.Backend.Services;

import com.DriverMileageTracker.Backend.Dto.RoleDTO;

import java.util.List;

public interface RoleService {
    List<RoleDTO> getAllRoles();
    RoleDTO getRoleById(Long id);
    RoleDTO createRole(RoleDTO roleDTO);
    RoleDTO updateRole(Long id, RoleDTO roleDTO);
    void deleteRole(Long id);
}
