package com.campus.resourcebooking.repositories;

import com.campus.resourcebooking.model.User;
import java.util.Optional;

public interface UserRepository extends Repository<User, String> {
    Optional<User> findByEmail(String email);
}
