package com.DriverMileageTracker.Backend.ServiceImpl;

import com.DriverMileageTracker.Backend.DTO.RoleDTO;
import com.DriverMileageTracker.Backend.Database.Role;
import com.DriverMileageTracker.Backend.Mappers.RoleMapper;
import com.DriverMileageTracker.Backend.Repository.RoleRepository;
import com.DriverMileageTracker.Backend.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMapper roleMapper;

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toDTO).toList();
    }
    public RoleDTO getRoleById(Long id) {
        return roleMapper.toDTO(roleRepository.findById(id).orElse(null));
    }
    public RoleDTO createRole(RoleDTO dto) {
        return roleMapper.toDTO(roleRepository.save(roleMapper.toEntity(dto)));
    }
    public RoleDTO updateRole(Long id, RoleDTO dto) {
        dto.setId(id);
        return roleMapper.toDTO(roleRepository.save(roleMapper.toEntity(dto)));
    }
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

}
