package com.ternobo.wallet.client.repositories;

import com.ternobo.wallet.client.records.ApplicationClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationClientRepository extends JpaRepository<ApplicationClient, Long>{
    Optional<ApplicationClient> findByClientToken(String clientToken);
    boolean deleteByClientToken(String clientToken);
}