package com.uver.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uver.dao.JoinDao;
import com.uver.dao.JoinDaoImpl;
import com.uver.vo.JoinVO;

@Service("JoinServiceImpl")
public class JoinServiceImpl implements JoinService {
	private static final Logger LOG = LoggerFactory.getLogger(JoinServiceImpl.class);
	@Autowired
	JoinDao joinDao;

	/**
	 * priority가 1 인경우 가장 빨리 참여한 id에 priority를 1증가 시킨 후 삭제
	 */
	@Override
	public int doDelete(JoinVO vo) {
		// TODO Auto-generated method stub
		int flag = 0;
		if (vo.getPriority() == 1) {
			int deleteFlag = joinDao.doDelete(vo);
			if (deleteFlag == 1) {
				int minRegId = joinDao.doSelectMinReg(vo.getEventSeq());
				
				int updateFlag = joinDao.doUpdate(new JoinVO(vo.getEventSeq(), minRegId, 1));
				LOG.debug("doUpdate() 실행" +updateFlag);
			} else {
				throw new RuntimeException("doDelete 실패");
			}
		} else {
			return joinDao.doDelete(vo);
		}
		//return flag;
		return flag;
	}
	
	@Override
	public int banMember(JoinVO masterVO, JoinVO targetVO) {
		// TODO Auto-generated method stub
		int flag = 0;
		if(masterVO.getPriority() ==1) {
			targetVO.setPriority(2);
			joinDao.doUpdate(targetVO);
			flag = 1;
		}
		return flag;
	}
	
	@Override
	public int kickMember(JoinVO masterVO, JoinVO targetVO) {
		// TODO Auto-generated method stub
		int flag = 0;
		if(masterVO.getPriority() ==1 && targetVO.getPriority() == 0 ){
			joinDao.doDelete(targetVO);
			flag = 1;
		}
		return flag;
	}

	// ----------------bypass----------------------
	@Override
	public int doInsert(JoinVO vo) {
		// TODO Auto-generated method stub
		return joinDao.doInsert(vo);
	}

	@Override
	public int doUpdate(JoinVO vo) {
		// TODO Auto-generated method stub
		return joinDao.doUpdate(vo);
	}

	@Override
	public JoinVO doSelectOne(JoinVO vo) {
		// TODO Auto-generated method stub
		return joinDao.doSelectOne(vo);
	}

	@Override
	public List doSelectList(JoinVO vo) {
		// TODO Auto-generated method stub
		return joinDao.doSelectList(vo);
	}







}