package com.springboot.restapi.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.restapi.entities.journal_header;

public interface JournalRespository extends JpaRepository<journal_header,Long>{

}
