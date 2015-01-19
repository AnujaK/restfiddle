package com.restfiddle.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RfRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

}
