package org.support.project.knowledge.logic.activity;

/**
 * ポイントが増減するアクティビティの種類
 * 
 * 種類  | ターゲット文字列 | イベントの内容
 * 1    | knowledge_id  | 記事を登録
 * 2    | knowledge_id  | 記事参照
 * 3    | knowledge_id  | 記事へイイネを登録
 * 4    | knowledge_id  | 記事をストック
 * 5    | knowledge_id  | アンケート回答
 * 6    | knowledge_id  | イベント参加
 * 101  | comment_no    | コメント登録
 * 102  | comment_no    | コメントにイイネ登録
 * -6   | knowledge_id  | イベント参加の取り消し
 * 
 * ---------------------------------------------------------------------------
 * 
 * ポイント操作
 * 
 * 種類  | 獲得のタイプ  | ポイント付与先 | ポイント    | 獲得タイプの意味
 * 1    | 11          | 記事登録者    | 50       | 記事を投稿したら投稿者にポイント追加
 * 1    | 12          | 記事         | 50       | 登録された記事のポイント初期値
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
 * 101  | 1011        | 登録者       | 20       | コメントを投稿すると、投稿者にポイント追加
 * 101  | 1012        | 記事         | 20       | 記事にコメントが付くと、その記事に対しポイント追加
 * 102  | 1021        | 参照者       | 2       | イイネを押すと、押した人にポイント追加
 * 102  | 1022        | 登録者       | 10       | コメントにイイネが付くと、そのコメントを登録したユーザにポイントが付く
 * 102  | 1023        | 記事         | 10       | コメントにイイネがつくと、そのコメントの記事に対しポイント追加
 * -6   | -61         | 参照者       | 5        | イベント参加者にポイント付与（取り消しなのでマイナス）
 * -6   | -62         | 記事登録者    | 5       | 記事の登録者にポイント追加（取り消しなのでマイナス）
 * -6   | -63         | 記事         | 5       | 記事のポイントが追加（取り消しなのでマイナス）
 * 
 * ユーザのポイントは、USER_CONFIGSテーブルへ格納する
 * ポイントはランダムで少し増減した方が面白い？？
 * 
 * @author koda
 */
public enum Activity {
    NONE,
    KNOWLEDGE_INSERT, // 記事登録
    KNOWLEDGE_SHOW, // 記事参照
    KNOWLEDGE_LIKE, // イイネを押した
    KNOWLEDGE_STOCK, // ストックした
    KNOWLEDGE_SURVEY, // アンケートに回答した
    KNOWLEDGE_EVENT_ADD, // イベントに参加した
    KNOWLEDGE_EVENT_DELETE, // イベント参加キャンセル
    KNOWLEDGE_COMMENT_ADD, // コメント追加
    KNOWLEDGE_COMMENT_LIKE; // コメントにイイネを押した
    
    public int getValue() {
        if (this == KNOWLEDGE_INSERT) {
            return 1;
        } else if (this ==KNOWLEDGE_SHOW) {
            return 2;
        } else if (this ==KNOWLEDGE_LIKE) {
            return 3;
        } else if (this ==KNOWLEDGE_STOCK) {
            return 4;
        } else if (this ==KNOWLEDGE_SURVEY) {
            return 5;
        } else if (this ==KNOWLEDGE_EVENT_ADD) {
            return 6;
        } else if (this ==KNOWLEDGE_EVENT_DELETE) {
            return -6;
        } else if (this ==KNOWLEDGE_COMMENT_ADD) {
            return 101;
        } else if (this ==KNOWLEDGE_COMMENT_LIKE) {
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
            return KNOWLEDGE_SURVEY;
        } else if (type == 6) {
            return KNOWLEDGE_EVENT_ADD;
        } else if (type == -6) {
            return KNOWLEDGE_EVENT_DELETE;
        } else if (type == 101) {
            return KNOWLEDGE_COMMENT_ADD;
        } else if (type == 102) {
            return KNOWLEDGE_COMMENT_LIKE;
        }
        return NONE;
    }
    
}

