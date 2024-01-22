package ru.practicum.ewm.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.users.dto.UserDto;
import ru.practicum.ewm.users.dto.UserMapper;
import ru.practicum.ewm.users.model.User;
import ru.practicum.ewm.users.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserDto createUser(UserDto request) {
        User user = UserMapper.toUser(request);
        User saveUser = userRepository.save(user);
        return UserMapper.toUserDto(saveUser);
    }

    public List<UserDto> getUsersByIds(List<Long> ids, Integer from, Integer size) {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            List<User> users = userRepository.findAll(PageRequest.of(from, size)).getContent();
            return UserMapper.toUserDtosList(users);
        }

        List<User> users = userRepository.findAllByIdIn(ids, PageRequest.of(from, size)).getContent();
        return UserMapper.toUserDtosList(users);
    }

    @Transactional
    public void deleteUserById(Long userId) {
        checkExistUserById(userId);
        userRepository.deleteById(userId);
    }

    public User getUserByIdIfExist(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    public void checkExistUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь не найден");
        }
    }
}