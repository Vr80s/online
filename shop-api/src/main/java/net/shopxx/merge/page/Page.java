package net.shopxx.merge.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Page<T> implements Serializable {

    /**
     * 内容
     */
    private final List<T> content = new ArrayList<>();

    /**
     * 总记录数
     */
    private final long total;

    /**
     * 分页信息
     */
    private final Pageable pageable;

    /**
     * 构造方法
     */
    public Page() {
        this.total = 0L;
        this.pageable = new Pageable();
    }

    /**
     * 构造方法
     *
     * @param content
     *            内容
     * @param total
     *            总记录数
     * @param pageable
     *            分页信息
     */
    public Page(List<T> content, long total, Pageable pageable) {
        this.content.addAll(content);
        this.total = total;
        this.pageable = pageable;
    }
    

    public List<T> getContent() {
        return content;
    }

    public long getTotal() {
        return total;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public static <T> Page<T> empty(Pageable pageable) {
        return new Page(Collections.<T>emptyList(), 0L, pageable);
    }
}
