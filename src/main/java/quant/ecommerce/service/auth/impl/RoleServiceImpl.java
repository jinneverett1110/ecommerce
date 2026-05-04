package quant.ecommerce.service.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quant.ecommerce.entity.auth.Role;
import quant.ecommerce.repository.RoleRepository;
import quant.ecommerce.service.auth.RoleService;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    private Integer clientRoleId;

    @Override
    public Integer getClientRoleId() {
        if(this.clientRoleId!=null){
            return clientRoleId;
        }
        Role role = roleRepository.findByName("CLIENT");
        clientRoleId = role.getId();
        return clientRoleId;
    }
}
