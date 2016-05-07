package com.ai.edc.common.container;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.edc.common.utils.CollectionUtil;

public class InstanceContainer {
	private static Logger logger = LogManager.getLogger(InstanceContainer.class);

    private static ConfigurableApplicationContext ctx;
    
    /**
     * 获取Spring托管的对象实例<br>
     * @param beanId
     * @return
     * @author wusheng
     */
    public static Object getBean(String beanId){
        initStatusCheck();
        return ctx.getBean(beanId);
    }
    
    /**
     * 是否存在Spring推官的对象实例
     * @param beanId
     * @return
     * @author wusheng
     */
    public static boolean hasBean(String beanId){
    	if(ctx == null){
    		return false;
    	}
        return ctx.containsBean(beanId);
    }
    
    /**
     * 根据类型选取注册类
     * @param beanClass
     * @return
     */
    public static <T> T findBean(Class<T> beanClass){
        initStatusCheck();
        String[] beanNames = ctx.getBeanNamesForType(beanClass);
        if(CollectionUtil.isEmpty(beanNames)){
        	return null;
        }
        logger.debug("find bean[" + beanClass.getName() + "] id=" + beanNames[0]);
        return ctx.getBean(beanNames[0], beanClass);
    }
    
    /**
     * 获取指定类型bean对象
     * @param <T>
     * @param beanClass
     * @param beanId
     * @return
     * @author wusheng
     */
    public static <T> T getBean(String beanId, Class<T> beanClass){
        initStatusCheck();
        return ctx.getBean(beanId, beanClass);
    }
    
    /**
     * 根据类型自动匹配bean对象
     * @param <T>
     * @param beanClass
     * @param beanId
     * @return
     * @author wusheng
     */
    public static <T> T getBean(Class<T> beanClass){
        initStatusCheck();
        return ctx.getBean(beanClass);
    }
    
    /**
     * 检查启动状态
     * 
     * @author wusheng
     */
    private static void initStatusCheck(){
        if(ctx == null){
            throw new RuntimeException("当前Spring上下文没有初始化，请检查应用是否正常启动");
        }
    }
    
    /**
     * Spring初始化<br>
     * 
     * @author wusheng
     */
    public synchronized static void init(String contextFileName){
        ctx = new ClassPathXmlApplicationContext(contextFileName); 
    } 
    
    public static void reflush(){
    	initStatusCheck();
    	ctx.refresh();
    }
}
