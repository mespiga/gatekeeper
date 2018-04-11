/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.data.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import app.data.jpa.domain.Usex;

import org.springframework.stereotype.Repository;
import javax.persistence.TransactionRequiredException;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import javax.persistence.EntityExistsException;

@Repository
public class JpaUsexRepository {
// public class JpaUsexRepository implements UsexRepository {

	// @PersistenceContext
	// private EntityManager entityManager;

	// // @Override
	// // @Transactional
	// // public List<AgentCM> findAll() {
	// // 	return this.entityManager.createQuery("SELECT n FROM AgentCM n", AgentCM.class)
	// // 			.getResultList();
	// // }

	// @Override
	// @Transactional
	// public List<Usex> findByEmail(String email) {
	// 	return this.entityManager.createQuery("SELECT n FROM Usex n where n.email = :aid", Usex.class)
	// 			.setParameter("aid", email)
	// 			.getResultList();
	// }


	// @Override
	// @Transactional
	// public List<Usex> findByEmailOnline(String email) {
	// 	return this.entityManager.createQuery("SELECT n FROM Usex n where n.email = :aid and n.active = :bon", Usex.class)
	// 			.setParameter("aid", email)
	// 			.setParameter("bon", true)
	// 			.getResultList();
	// }


	// @Override
	// @Transactional
	// public Usex findByEmailandPasswd(String email, String passwd) {
	// 	// add encoding passwd
	// 	return this.entityManager.createQuery("SELECT n FROM Usex n where n.email = :aid and n.password = :pwd", Usex.class)
	// 			.setParameter("aid", email)
	// 			.setParameter("pwd", passwd)
	// 			.getSingleResult();
	// }

	// @Override
	// @Transactional
 //    public synchronized Usex add(Usex user) {
 //        try{
 //        	System.out.println("add User: " + user.getProducts().size());
	//         this.entityManager.persist(user);
	//         this.entityManager.flush();
	//         return user;
 //        }catch(EntityExistsException e){
	// 		System.out.println("ERROR: " + e.getMessage());
	// 		e.printStackTrace();
	// 		return null;
			
 //    	}
 //    }

 //    @Override
	// @Transactional
 //    public synchronized Usex merge(Usex user) {
 //        try{
 //        	System.out.println("merge User: " + user.getProducts().size());
	//         this.entityManager.merge(user);
	//         this.entityManager.flush();
	//         return user;
 //        }catch(EntityExistsException e){
	// 		System.out.println("ERROR: " + e.getMessage());
	// 		e.printStackTrace();
	// 		return null;
			
 //    	}
 //    }


 //    @Override
 //    @Transactional
 //    public synchronized int updateOnline(String email, Boolean online) {
	// 	try{
	// 		int res = this.entityManager.createQuery("UPDATE Usex a SET a.active= :dt WHERE a.email= :aid")
	// 			.setParameter("dt", online)
	// 			.setParameter("aid", email)
	// 			.executeUpdate();
	// 		return res;
	// 	}catch(TransactionRequiredException e){
	// 		System.out.println("ERROR: " + e.getMessage());
	// 		e.printStackTrace();
	// 		return 0;
 //    	}
 //    }

 //    @Override
 //    @Transactional
 //    public synchronized int softDeleteById(Long id) {
	// 	try{
	// 		int res = this.entityManager.createQuery("UPDATE Usex a SET a.active= :dt WHERE a.id= :aid")
	// 			.setParameter("dt", false)
	// 			.setParameter("aid", id)
	// 			.executeUpdate();
	// 		return res;
	// 	}catch(TransactionRequiredException e){
	// 		System.out.println("ERROR: " + e.getMessage());
	// 		e.printStackTrace();
	// 		return 0;
 //    	}
 //    }

 //    @Override
 //    @Transactional
 //    public synchronized Usex findById(Long id){
 //    	return this.entityManager.createQuery("SELECT n FROM Usex n where n.id = :aid", Usex.class)
	// 			.setParameter("aid", id)
	// 			.getSingleResult();

 //    }

 //    @Override
 //    @Transactional
 //    public synchronized List<Usex> findChildrensFromParent(Long userId){
 //    	return this.entityManager.createQuery("SELECT n FROM Usex n where n.parentId = :aid", Usex.class)
	// 			.setParameter("aid", userId)
	// 			.getResultList();

 //    }


 // //    @Override
	// // @Transactional
 // //    public void remove(AgentCM agentCM){
 // //    	// System.out.println("REMOVE AGENT: " + agentCM.getAgentId());
 // //   //  	try{
 // //   //  		int res = this.entityManager.createQuery("DELETE From AgentCM a WHERE a.agentId= :aid")
	// // 		// 	.setParameter("aid", agentCM.getAgentId())
	// // 		// 	.executeUpdate();
 // //   //  	}catch(TransactionRequiredException e){
 // //   //  		System.out.println("ERROR: " + e.getMessage());
	// // 		// e.printStackTrace();
 // //   //  	}
	// // 	return;
 // //    }

	// // @Override
	// // @Transactional
 // //    public void removeAll(){
    	
	// // 	this.entityManager.createQuery("DELETE FROM AgentCM").executeUpdate();;
 // //    	return;
 // //    }

 // //    @Override
	// // @Transactional
 // //    public int addList(List<AgentCM> agentList){
 // //    	for(AgentCM agentCM : agentList){
 // //    		this.entityManager.persist(agentCM);
 // //    	}
 // //    	return agentList.size();

 // //    }
}
