package cn.cxnxs.letuschat.service.impl;

import cn.cxnxs.letuschat.entity.GroupUserRel;
import cn.cxnxs.letuschat.mapper.GroupUserRelMapper;
import cn.cxnxs.letuschat.service.IGroupUserRelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 群-用户关联表 服务实现类
 * </p>
 *
 * @author mengjinyuan
 * @since 2020-10-19
 */
@Service
public class GroupUserRelServiceImpl extends ServiceImpl<GroupUserRelMapper, GroupUserRel> implements IGroupUserRelService {

}
