package ${basepackage}.service.impl;

import ${basepackage}.dao.${pojo.shortName}Dao;
import ${basepackage}.model.${pojo.shortName};
import ${basepackage}.service.${pojo.shortName}Manager;
import ${appfusepackage}.service.impl.GenericManagerImpl;

import java.util.List;
import javax.jws.WebService;

@WebService(serviceName = "${pojo.shortName}Service", endpointInterface = "${basepackage}.service.${pojo.shortName}Manager")
public class ${pojo.shortName}ManagerImpl extends GenericManagerImpl<${pojo.shortName}, ${pojo.getJavaTypeName(pojo.identifierProperty, jdk5)}> implements ${pojo.shortName}Manager {
    ${pojo.shortName}Dao ${pojo.shortName.toLowerCase()}Dao;

    public ${pojo.shortName}ManagerImpl(${pojo.shortName}Dao ${pojo.shortName.toLowerCase()}Dao) {
        super(${pojo.shortName.toLowerCase()}Dao);
        this.${pojo.shortName.toLowerCase()}Dao = ${pojo.shortName.toLowerCase()}Dao;
    }
}
