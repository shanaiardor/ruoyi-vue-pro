package cn.iocoder.yudao.module.bpm.framework.flowable.core.candidate.strategy;

import cn.iocoder.yudao.framework.common.util.collection.SetUtils;
import cn.iocoder.yudao.module.bpm.framework.flowable.core.candidate.BpmTaskCandidateStrategy;
import cn.iocoder.yudao.module.bpm.framework.flowable.core.enums.BpmTaskCandidateStrategyEnum;
import cn.iocoder.yudao.module.bpm.service.task.BpmProcessInstanceService;
import jakarta.annotation.Resource;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 发起人自己 {@link BpmTaskCandidateUserStrategy} 实现类。 用于需要发起人信息复核等场景
 *
 * @author jason
 */
@Component
public class BpmTaskCandidateStartUserStrategy implements BpmTaskCandidateStrategy {

    @Resource
    @Lazy // 延迟加载，避免循环依赖
    private BpmProcessInstanceService processInstanceService;

    @Override
    public BpmTaskCandidateStrategyEnum getStrategy() {
        return BpmTaskCandidateStrategyEnum.START_USER;
    }

    /**
     * 无需校验参数
     */
    @Override
    public void validateParam(String param) {}

    @Override
    public Set<Long> calculateUsers(DelegateExecution execution, String param) {
        String startUserId = processInstanceService.getProcessInstance(execution.getProcessInstanceId()).getStartUserId();
        return SetUtils.asSet(Long.valueOf(startUserId));
    }

    /**
     * 不需要参数
     */
    @Override
    public boolean isParamRequired() {
        return false;
    }
}