package org.eleven.mapper;

import org.eleven.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author lin3fei@126.com
 * @since 2023-05-24
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
