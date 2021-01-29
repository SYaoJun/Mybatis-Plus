package com.hysteria.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.*;


/**
 * mybatis plus mysql 代码生成器
 * @author: blackcat
 * @date: 2020/1/6 17:22
 * @Param
 */
public class CodeGenerator {

        public static void main(String[] args) {
                Map<String, String> param = new HashMap<>();
                param.put("projectPath","F:\\JavaCode\\mp-springboot");// 代码输出项目地址
                param.put("author","yaojun");// 作者
                param.put("url","127.0.0.1:3306/yaojun");// ip/数据库
                param.put("driverName","com.mysql.cj.jdbc.Driver");
                param.put("username","root");// 数据库用户
                param.put("password","123456");// 数据库密码
                param.put("parent","com.hysteria");// 顶层包结构
                param.put("mapper","mapper");// 生成的mapper包名
                param.put("entity","entity");// 生成的entity包名
                param.put("service","service");// 生成的service包名
                param.put("serviceImpl","service.impl");// 生成的serviceImpl包名
                param.put("xml","mappers");// 生成的mapper.xml包名
                param.put("model","");// 生成的mapper.xml包名下的模块名称 空则无 如:mappers.shiro
                param.put("xmlName","Mapper");// 生成的mapper.xml的文件结尾名称如UserMapper.xml
                // 设置模板 freemarker模板:/templates/mapper.xml.ftl  velocity模板:/templates/mapper.xml.vm
                param.put("templatePath","/templates/mapper.xml.ftl");//
                param.put("table","");//数据库表名 一张表时使用
                // 多表时使用,当数组大于0时使用数组
                // 示例:String[] tablse={"sys_menu","sys_role","sys_role_menu","sys_user_role","sys_user_role"};// shiro所需表
                //所需表
                String[] tablse={"user"};
                generator(param,tablse);
        }

        public static void generator(Map<String, String> param,String[] tablse){
                // 代码生成器
                AutoGenerator mpg = new AutoGenerator();

                // 全局配置
                GlobalConfig gc = new GlobalConfig();
                gc.setOutputDir(param.get("projectPath") + "/src/main/java");
                gc.setAuthor(param.get("author"));
                gc.setOpen(false);// 是否打开输出目录
                gc.setServiceName("%sService");// service 命名方式
                gc.setServiceImplName("%sServiceImpl");// service impl 命名方式
                gc.setMapperName("%sMapper");// 自定义文件命名，注意 %s 会自动填充表实体属性！
                gc.setXmlName("%sMapper");// 自定义文件命名，注意 %s 会自动填充表实体属性！
                gc.setFileOverride(true);// 是否覆盖已有文件
                gc.setActiveRecord(true);// 开启 ActiveRecord(活动记录) 模式
                gc.setEnableCache(false);// XML 二级缓存
                gc.setBaseResultMap(true);// XML 开启 BaseResultMap
                gc.setBaseColumnList(false);// XML columList
                mpg.setGlobalConfig(gc);

                // 数据源配置
                DataSourceConfig dsc = new DataSourceConfig();
                dsc.setUrl("jdbc:mysql://"+param.get("url")+"?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC");
                dsc.setDriverName(param.get("driverName"));
                dsc.setUsername(param.get("username"));
                dsc.setPassword(param.get("password"));
                mpg.setDataSource(dsc);

                // 包配置
                PackageConfig pc = new PackageConfig();
                if(!StringUtils.isEmpty(param.get("model"))){
                        pc.setModuleName(param.get("model"));
                }
                pc.setParent(param.get("parent"));
                pc.setMapper(param.get("mapper"));
                pc.setEntity(param.get("entity"));
                pc.setService(param.get("service"));
                pc.setServiceImpl(param.get("serviceImpl"));
                pc.setXml(param.get("xml"));
                mpg.setPackageInfo(pc);

                // 自定义需要填充的字段
                List<TableFill> tableFillList = new ArrayList();
                //如 每张表都有一个创建时间、修改时间如下是配置
                TableFill createField = new TableFill("gmt_create", FieldFill.INSERT);
                TableFill modifiedField = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
                tableFillList.add(createField);
                tableFillList.add(modifiedField);


                // 自定义配置
                InjectionConfig cfg = new InjectionConfig() {
                        @Override
                        public void initMap() {
                                // to do nothing
                        }
                };
                // 模板引擎
                String templatePath = param.get("templatePath");
                // 自定义输出配置
                List<FileOutConfig> focList = new ArrayList<>();
                // 自定义配置会被优先输出 这里设置xml的存放路径
                focList.add(new FileOutConfig(templatePath) {
                        @Override
                        public String outputFile(TableInfo tableInfo) {
                                // 自定义输出文件名
                                StringBuilder customPath = new StringBuilder();
                                customPath.append(param.get("projectPath"));
                                customPath.append("/src/main/resources/");
                                customPath.append(param.get("xml"));
                                customPath.append("/");
                                if(!StringUtils.isEmpty(pc.getModuleName())){
                                        customPath.append(pc.getModuleName());
                                }
                                customPath.append("/");
                                customPath.append(tableInfo.getEntityName());
                                customPath.append(param.get("xmlName"));
                                customPath.append(StringPool.DOT_XML);
               /* return param.get("projectPath") + "/src/main/resources/"+param.get("xml")+"/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;*/
                                return customPath.toString();
                        }
                });
                cfg.setFileOutConfigList(focList);
                mpg.setCfg(cfg);

                // 策略配置
                StrategyConfig strategy = new StrategyConfig();
                //数据库表映射到实体的命名策略，默认：不做任何改变，原样输出
                strategy.setNaming(NamingStrategy.underline_to_camel);
                //数据库表字段映射到实体的命名策略，未指定按照 naming 执行
                strategy.setColumnNaming(NamingStrategy.underline_to_camel);
                strategy.setEntityLombokModel(true);// 是否使用lombok
                if (tablse.length > 0) {
                        strategy.setInclude(tablse);// 数据库表 多表
                } else {
                        strategy.setInclude(param.get("table"));// 数据库表 一张表
                }
                strategy.setControllerMappingHyphenStyle(true);// 驼峰转连字符
                //strategy.setSuperControllerClass("com.sxt.BaseController");// 公共父类
                //strategy.setSuperEntityColumns("person_id","person_name");// 写于父类中的公共字段
                //strategy.setInclude(("表名，多个英文逗号分割").split(","));//要设置生成哪些表 如果不设置就是生成所有的表
                mpg.setStrategy(strategy);// 数据库表配置
                // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！ 默认 Veloctiy
                mpg.setTemplateEngine(new FreemarkerTemplateEngine());
                mpg.execute();
        }
}
