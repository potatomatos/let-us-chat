package cn.cxnxs.letuschat.service.impl;

import cn.cxnxs.letuschat.entity.Groups;
import cn.cxnxs.letuschat.mapper.GroupsMapper;
import cn.cxnxs.letuschat.service.IGroupsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 群组 服务实现类
 * </p>
 *
 * @author mengjinyuan
 * @since 2020-10-19
 */
@Service
public class GroupsServiceImpl extends ServiceImpl<GroupsMapper, Groups> implements IGroupsService {

}
