package cn.cxnxs.letuschat.service.impl;

import cn.cxnxs.letuschat.entity.Messages;
import cn.cxnxs.letuschat.mapper.MessagesMapper;
import cn.cxnxs.letuschat.service.IMessagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author mengjinyuan
 * @since 2020-10-17
 */
@Service
public class MessagesServiceImpl extends ServiceImpl<MessagesMapper, Messages> implements IMessagesService {

}
