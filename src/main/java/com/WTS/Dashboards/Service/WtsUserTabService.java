package com.WTS.Dashboards.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WTS.Dashboards.Entity.WtsUserTab;
import com.WTS.Dashboards.dao.WtsUserTabDao;

@Service
public class WtsUserTabService implements IWtsServiceInterface {

	@Autowired
	private WtsUserTabDao userDao;
	
	public List<WtsUserTab> getAlluser() {
		return userDao.getAllUser();
	}

	
	public synchronized boolean addUser(WtsUserTab usr) {
		if(userDao.UserExists(usr.getUserId())){
			return false;
		}
			else
			{
				userDao.addUser(usr);;
				return true;
			}
	}

	
	public void updateUser(WtsUserTab usr) {
		userDao.updateUser(usr);
		
	}

	
	public void deleteUser(int usrId) {
		userDao.deleteUser(usrId);
		
	}

	
	public WtsUserTab getUserById(int usrId) {
		WtsUserTab us= userDao.getUserById(usrId);
		return us;
	}

}
