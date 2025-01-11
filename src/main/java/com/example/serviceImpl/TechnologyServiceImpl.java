package com.example.serviceImpl;

import com.example.dto.ResponseDTO;
import com.example.dto.TechnologyDTO;
import com.example.entity.Technology;
import com.example.entity.User;
import com.example.enums.UserType;
import com.example.repository.TechnologyRepository;
import com.example.repository.UserRepository;
import com.example.service.TechnologyService;
import com.example.util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TechnologyServiceImpl implements TechnologyService {

    @Autowired
    TechnologyRepository technologyRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseDTO addTechnology(TechnologyDTO technologyDTO) {
        Optional<User> user = userRepository.findById(technologyDTO.getUserId());
        Technology technology = new Technology();
        if (user.get().getRole().equals(UserType.ADMIN)) {
            technology.setName(technologyDTO.getName());
            technology.setUserId(technologyDTO.getUserId());
            technologyRepository.save(technology);
            return new ResponseDTO(ResponseMessage.API_SUCCESS_CODE, ResponseMessage.ADDED_SUCCESSFULLY, technology);
        } else {
            return new ResponseDTO(ResponseMessage.BAD_REQUEST, ResponseMessage.UNAUTHORIZED_USER_ROLE, null);
        }
    }
    @Override
     public void deleteTechByAdmin(Integer id) {
        technologyRepository.deleteById(id);
    }
}