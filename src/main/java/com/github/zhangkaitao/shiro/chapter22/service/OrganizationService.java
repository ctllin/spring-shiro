package com.github.zhangkaitao.shiro.chapter22.service;

import com.github.zhangkaitao.shiro.chapter22.entity.Organization;

import java.util.List;

/**
 * <p>User: ctllin
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface OrganizationService {


    public Organization createOrganization(Organization organization);
    public Organization updateOrganization(Organization organization);
    public void deleteOrganization(Long organizationId);

    Organization findOne(Long organizationId);
    List<Organization> findAll();

    Object findAllWithExclude(Organization excludeOraganization);

    void move(Organization source, Organization target);
}