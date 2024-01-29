package ru.practicum.ewm.categories.repostiory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.categories.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}