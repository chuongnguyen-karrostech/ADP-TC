package com.APIMM.etgps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.APIMM.etgps.model.Message;

public interface MessageRepository  extends JpaRepository<Message, Long>{

}
