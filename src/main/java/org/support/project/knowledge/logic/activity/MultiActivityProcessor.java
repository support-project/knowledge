package org.support.project.knowledge.logic.activity;


/**
 * 通常のActivityProcessorは、単一のActivityを処理することを想定しているが、
 * 複数のActivityを一つのクラスで処理してしまいたいこともある。
 * この場合、どのActivityとして呼び出されたかを保持する必要があるので、Activityを外部からセットするIFを持つ
 * @author koda
 */
public interface MultiActivityProcessor {
    void setActivity(Activity activity);
}
