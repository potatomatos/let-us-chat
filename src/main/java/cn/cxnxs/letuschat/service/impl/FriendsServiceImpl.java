package cn.cxnxs.letuschat.service.impl;

import cn.cxnxs.letuschat.entity.Friends;
import cn.cxnxs.letuschat.mapper.FriendsMapper;
import cn.cxnxs.letuschat.service.IFriendsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 好友表 服务实现类
 * </p>
 *
 * @author mengjinyuan
 * @since 2020-10-19
 */
@Service
public class FriendsServiceImpl extends ServiceImpl<FriendsMapper, Friends> implements IFriendsService {

}
