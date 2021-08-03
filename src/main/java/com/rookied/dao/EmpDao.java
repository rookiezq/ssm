package com.rookied.dao;

import com.rookied.bean.Emp;
import org.springframework.stereotype.Repository;

/**
 * @author zhangqiang
 * @date 2021/8/3
 */
@Repository
public interface EmpDao {
    Emp findEmpByEmpno(Integer empno);
}
