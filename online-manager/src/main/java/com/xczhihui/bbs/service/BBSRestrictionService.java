package com.xczhihui.bbs.service;

import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.utils.TableVo;

import java.util.List;

/**
 * 用户禁言拉黑服务类
 *
 * @author hejiwei
 */
public interface BBSRestrictionService {

    /**
     * 用户列表
     *
     * @param tableVo 搜索条件封装
     * @return 列表数据
     */
    TableVo list(TableVo tableVo);

    /**
     * 批量禁言/取消禁言
     *
     * @param userIds 用户id列表
     * @param gags    禁言状态
     * @return 操作结果
     */
    ResponseObject updateGags(List<String> userIds, boolean gags);

    /**
     * 批量拉黑/取消拉黑
     *
     * @param userIds   用户id列表
     * @param blacklist 拉黑状态
     * @return 操作结果
     */
    ResponseObject updateBlacklist(List<String> userIds, boolean blacklist);

    /**
     * 是否已取消拉黑/禁言
     *
     * @param userIds   用户id列表
     * @param gagsOrBlacklist 1拉黑2禁言
     * @return 操作结果
     */
    ResponseObject isGagsOrBlacklist(List<String> userIds, Integer gagsOrBlacklist);
}
