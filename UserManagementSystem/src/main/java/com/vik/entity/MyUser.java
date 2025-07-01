package com.vik.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USER_MNGMNT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyUser {

	@Id
    @SequenceGenerator(name = "user", sequenceName = "USER_SEQ", allocationSize = 1, initialValue = 100)
    @GeneratedValue(generator = "user", strategy = GenerationType.SEQUENCE)
	private Integer id;
	
    @NonNull
    @Column(length = 30)
	private String email;
    
    @NonNull
    @Column(length = 90)
	private String password;
    
    @NonNull
    @Column(length = 30)
	private String role;
    
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime registerDateTime;
    
    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updationDateTime;
    
    @Version
    private Integer updateCount;
	
	
}
