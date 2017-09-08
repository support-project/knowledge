package org.support.project.knowledge.logic.activity;

/**
 * ポイントが増減するアクティビティの種類
 * 
 * 種類  | ターゲット文字列 | イベントの内容
 * 1    | knowledge_id  | 記事を登録(使わない）
 * 2    | knowledge_id  | 記事参照
 * 3    | knowledge_id  | 記事へイイネを登録
 * 4    | knowledge_id  | 記事をストック
 * 5    | knowledge_id  | アンケート回答
 * 6    | knowledge_id  | イベント参加
 * 10   | knowledge_id  | 記事を「公開」で投稿（登録／更新時）→増える
 * 11   | knowledge_id  | 記事を「保護」で投稿（登録／更新時）→前が非公開であれば増える、前が公開であれば減る
 * 12   | knowledge_id  | 記事を「非公開」で投稿（登録／更新時）→前が「公開」「保護」の場合減る（前が「公開」「保護」で無い場合登録しない/非公開の記事ではポイント増えない）
 * 101  | comment_no    | コメント登録
 * 103  | comment_no    | コメントにイイネ登録
 * -6   | knowledge_id  | イベント参加の取り消し
 * 
 * ---------------------------------------------------------------------------
 * 
 * ポイント操作
 * 
 * 種類  | 獲得のタイプ  | ポイント付与先 | ポイント    | 獲得タイプの意味
 * 1    | 11          | 記事登録者    | 50       | 記事を投稿したら投稿者にポイント追加(使わない）
 * 1    | 13          | 記事         | 50       | 登録された記事のポイント初期値(使わない）
 * 2    | 21          | 参照者       | 1        | 記事を参照するアクションを行うと、参照者にポイント追加（一つの記事に付き1回のみ）
 * 2    | 22          | 記事登録者    | 1        | 自分が登録された記事が参照されたら、登録者にポイント追加（一つの記事に対し、参照者毎に1回のみ）
 * 2    | 23          | 記事         | 1        | 記事が参照されると、その記事のポイントが追加（一つの記事に対し、参照者毎に1回のみ）
 * 3    | 31          | 参照者       | 2        | 記事にイイネのアクションを行うと、参照者にポイント追加（一つの記事に付き1回のみ）
 * 3    | 32          | 記事登録者    | 10       | 自分が登録された記事にイイネがついたら、登録者にポイント追加（一つの記事に対し、参照者毎に1回のみ）
 * 3    | 33          | 記事         | 10       | 記事が参照されると、その記事のポイントが追加（一つの記事に対し、参照者毎に1回のみ）
 * 4    | 41          | 参照者       | 0        | ストックした場合、ストックした人にポイントは付与しない
 * 4    | 42          | 記事登録者    | 2       | 記事の登録者にポイント追加（一つの記事に対し、参照者毎に1回のみ）
 * 4    | 43          | 記事         | 2       | 記事のポイントが追加（一つの記事に対し、参照者毎に1回のみ）
 * 5    | 51          | 参照者       | 3        | アンケート回答者にポイント付与
 * 5    | 52          | 記事登録者    | 3       | 記事の登録者にポイント追加（一つの記事に対し、参照者毎に1回のみ）
 * 5    | 53          | 記事         | 3       | 記事のポイントが追加（一つの記事に対し、参照者毎に1回のみ）
 * 6    | 61          | 参照者       | 5        | イベント参加者にポイント付与
 * 6    | 62          | 記事登録者    | 5       | 記事の登録者にポイント追加（一つの記事に対し、参照者毎に1回のみ）
 * 6    | 63          | 記事         | 5       | 記事のポイントが追加（一つの記事に対し、参照者毎に1回のみ）
 * 10   | 101         | 記事登録者    | 50       | 公開になった時点でトータル50になるように
 * 10   | 103         | 記事         | 50       | 
 * 11   | 111         | 記事登録者    | 30       | 
 * 11   | 113         | 記事         | 30       | 
 * 12   | 121         | 記事登録者    | 0        | このアクティビティの前に、投稿のアクティビティがあった場合、それを打ち消す（マイナスのポイント）
 * 12   | 123         | 記事         | 0        | 
 * 101  | 1011        | 登録者       | 20       | コメントを投稿すると、投稿者にポイント追加
 * 101  | 1013        | 記事         | 20       | 記事にコメントが付くと、その記事に対しポイント追加
 * 103  | 1031        | 参照者       | 2       | イイネを押すと、押した人にポイント追加
 * 103  | 1032        | 登録者       | 10       | コメントにイイネが付くと、そのコメントを登録したユーザにポイントが付く
 * 103  | 1033        | 記事         | 10       | コメントにイイネがつくと、そのコメントの記事に対しポイント追加
 * -6   | -61         | 参照者       | 5        | イベント参加者にポイント付与（取り消しなのでマイナス）
 * -6   | -62         | 記事登録者    | 5       | 記事の登録者にポイント追加（取り消しなのでマイナス）
 * -6   | -63         | 記事         | 5       | 記事のポイントが追加（取り消しなのでマイナス）
 * 
 * ユーザのポイントは、USER_CONFIGSテーブルへ格納する
 * ポイントはランダムで少し増減した方が面白い？？
 * ポイントは、だいたいの定義で、各実装の処理内で拡張する（例えば、イイネは、件数が増える毎に、ナレッジに付くポイントは増加するとか）
 * 
 * @author koda
 */
public enum Activity {
    NONE,
    KNOWLEDGE_INSERT, // 記事登録
    KNOWLEDGE_SHOW, // 記事参照
    KNOWLEDGE_LIKE, // イイネを押した
    KNOWLEDGE_STOCK, // ストックした
    KNOWLEDGE_ANSWER, // アンケートに回答した
    KNOWLEDGE_EVENT_ADD, // イベントに参加した
    KNOWLEDGE_EVENT_DELETE, // イベント参加キャンセル
    KNOWLEDGE_POST_PUBLIC, // 公開で投稿
    KNOWLEDGE_POST_PROTECTED, // 保護で投稿
    KNOWLEDGE_POST_PRIVATE, // 非公開で投稿
    COMMENT_INSERT, // コメント追加
    COMMENT_LIKE; // コメントにイイネを押した
    
    public int getValue() {
        if (this == KNOWLEDGE_INSERT) {
            return 1;
        } else if (this ==KNOWLEDGE_SHOW) {
            return 2;
        } else if (this ==KNOWLEDGE_LIKE) {
            return 3;
        } else if (this ==KNOWLEDGE_STOCK) {
            return 4;
        } else if (this ==KNOWLEDGE_ANSWER) {
            return 5;
        } else if (this ==KNOWLEDGE_EVENT_ADD) {
            return 6;
        } else if (this ==KNOWLEDGE_EVENT_DELETE) {
            return -6;
        } else if (this ==KNOWLEDGE_POST_PUBLIC) {
            return 10;
        } else if (this ==KNOWLEDGE_POST_PROTECTED) {
            return 11;
        } else if (this ==KNOWLEDGE_POST_PRIVATE) {
            return 12;
        } else if (this ==COMMENT_INSERT) {
            return 101;
        } else if (this ==COMMENT_LIKE) {
            return 102;
        }
        return Integer.MIN_VALUE;
    }

    public static Activity getType(int type) {
        if (type == 1) {
            return KNOWLEDGE_INSERT;
        } else if (type == 2) {
            return KNOWLEDGE_SHOW;
        } else if (type == 3) {
            return KNOWLEDGE_LIKE;
        } else if (type == 4) {
            return KNOWLEDGE_STOCK;
        } else if (type == 5) {
            return KNOWLEDGE_ANSWER;
        } else if (type == 6) {
            return KNOWLEDGE_EVENT_ADD;
        } else if (type == -6) {
            return KNOWLEDGE_EVENT_DELETE;
        } else if (type == 10) {
            return KNOWLEDGE_POST_PUBLIC;
        } else if (type == 11) {
            return KNOWLEDGE_POST_PROTECTED;
        } else if (type == 12) {
            return KNOWLEDGE_POST_PRIVATE;
        } else if (type == 101) {
            return COMMENT_INSERT;
        } else if (type == 102) {
            return COMMENT_LIKE;
        }
        return NONE;
    }
    
}

