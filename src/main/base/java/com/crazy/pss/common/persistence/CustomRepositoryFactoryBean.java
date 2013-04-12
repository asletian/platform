package com.crazy.pss.common.persistence;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class CustomRepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends Serializable> extends JpaRepositoryFactoryBean<JpaRepository<S,ID>, S, ID>{

	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new CustomRepositoryFactory(entityManager);
	}
	
	private static class CustomRepositoryFactory<S, ID extends Serializable> extends JpaRepositoryFactory {
		
		private EntityManager entityManager;
		
		public CustomRepositoryFactory(EntityManager entityManager) {
			super(entityManager);
			
			this.entityManager = entityManager;
		}

		protected Object getTargetRepository(RepositoryMetadata metadata) {
			return new CustomRepository<S, ID>((Class<S>) metadata.getDomainType(), entityManager);
		}
		
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			// The RepositoryMetadata can be safely ignored, it is used by the JpaRepositoryFactory
			//to check for QueryDslJpaRepository's which is out of scope.
			return ICustomRepository.class;
		}
	}
}
