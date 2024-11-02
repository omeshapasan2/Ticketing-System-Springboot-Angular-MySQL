package com.example.demo.repository;

import com.example.demo.entity.Vendors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // this annotation tells Spring this interface handles database interactions.
public interface VendorRepository extends JpaRepository<Vendors,Long> {
    // This interface extends JpaRepository, which provides CRUD operations and database interaction for the Vendors entity.
    // The Vendors entity is the type being managed, and Long is the type of the primary key.

}
