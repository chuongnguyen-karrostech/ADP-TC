package com.APIMM.mam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.APIMM.mam.model.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Integer> {
	@Query("select a from Theme a where a.id = ?1")
    Theme findTheme(Integer themeId);
	
	@Query("select a from Theme a where a.isDefault = true")
    Theme findDefaultTheme();
}
