package com.hnxt.activiti.service;

import com.hnxt.activiti.vo.ActDeploymentEntity;
import com.hnxt.activiti.vo.WorkFlowVo;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @Author yinz
 * @Date 2021/11/24 - 15:18
 */
@Service
public class WorkFlowService {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private FormService formService;

	@Autowired
	private ManagementService managementService;

	public List<ActDeploymentEntity> loadAllDeployment(WorkFlowVo workFlowVo) {
		if (workFlowVo.getDeploymentName() == null) {
			workFlowVo.setDeploymentName("");
		}
		String name = workFlowVo.getDeploymentName();
		// 查询总条数
		long count = repositoryService.createDeploymentQuery().deploymentNameLike("%" + name + "%").count();
		// 查询
		int firstResult = (workFlowVo.getPage() - 1) * workFlowVo.getLimit();
		int maxResults = workFlowVo.getLimit();
		List<Deployment> list = repositoryService.createDeploymentQuery().deploymentNameLike("%" + name + "%")
				.listPage(firstResult, maxResults);
		List<ActDeploymentEntity> data = new ArrayList<ActDeploymentEntity>();
		for (Deployment deployment : list) {
			ActDeploymentEntity entity = new ActDeploymentEntity();
			// copy
			BeanUtils.copyProperties(deployment, entity);
			data.add(entity);
		}
		return data;
	}

	// 部署流程
	public void addWorkFlow(InputStream inputStream, String deploymentName) {
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		this.repositoryService.createDeployment().name(deploymentName).addZipInputStream(zipInputStream).deploy();
		try {
			zipInputStream.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
