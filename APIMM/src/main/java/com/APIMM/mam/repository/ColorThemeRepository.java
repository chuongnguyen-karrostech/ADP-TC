package com.APIMM.mam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.APIMM.mam.model.ColorTheme;
@Repository
public interface ColorThemeRepository extends JpaRepository<ColorTheme, Integer>{
	
	@Query("select c from ColorTheme c where c.themeId = ?1")
	List<ColorTheme> findAllColorThemeByThemeId (Integer themeId);
	
}
