package org.support.project.common.util;

import java.io.ByteArrayOutputStream;

public class XmlUtils {

	
	
	/**
	 * xmlの中で利用できない制御文字を消す
	 * @param xml Xmlの文字列
	 * @return 制御文字を消した文字列
	 */
	public static String convControlChar(final String xml) {
		// 以下の制御文字は全て消去
//		00	NUL	^@	NULl（ヌル）
//		01	SOH	^A	Start Of Heading（ヘッダ開始）
//		02	STX	^B	Start of TeXt（テキスト開始）
//		03	ETX	^C	End of TeXt（テキスト終了）
//		04	EOT	^D	End Of Transmission（転送終了）
//		05	ENQ	^E	ENQuiry（問合せ）
//		06	ACK	^F	ACKnowledge（肯定応答）
//		07	BEL	^G	BELl（ベル）
//		08	BS	^H	Back Space（後退）
//		09	HT	^I	Horizontal Tabulation（水平タブ）
//		0A	LF	^J	Line Feed（改行）
//		0B	VT	^K	Vertical Tabulation（垂直タブ）
//		0C	FF	^L	Form Feed（改ページ）
//		0D	CR	^M	Carriage Return（復帰）
//		0E	SO	^N	Shift Out（シフトアウト）
//		0F	SI	^O	Shift In（シフトイン）
//		10	DLE	^P	Data Link Escape（伝送制御拡張）
//		11	DC1	^Q	Device Control 1（装置制御1）
//		12	DC2	^R	Device Control 2（装置制御2）
//		13	DC3	^S	Device Control 3（装置制御3）
//		14	DC4	^T	Device Control 4（装置制御4）
//		15	NAK	^U	Negative AcKnowledge（否定応答）
//		16	SYN	^V	SYNchronous idle（同期信号）
//		17	ETB	^W	End of Transmission Block（転送ブロック終了）
//		18	CAN	^X	CANcel（取消）
//		19	EM	^Y	End of Medium（媒体終端）
//		1A	SUB	^Z	SUBstitute（置換）
//		1B	ESC	^[	ESCape（拡張）
//		1C	FS	^\	File Separator（ファイル分離）
//		1D	GS	^]	Group Separator（グループ分離）
//		1E	RS	^^	Record Separator（レコード分離）
//		1F	US	^_	Unit Separator（ユニット分離）
		// 00～1Fまで
		//LFとCRは除外する

//		String convXml = xml;
//		for (byte i = 0x00; i <= 0x1f; i++) {
//			byte[] value = { i };
//			if (i != 0x0A && i != 0x0D) {
//				String replaceStr = new String(value);
//				if (xml.indexOf(replaceStr) != -1) {
//					convXml = xml.replaceAll(replaceStr, "");
//					if (LOG.isTraceEnabled()) {
//						LOG.trace("remove control character : " +  replaceStr + "[" + Long.toHexString(i) + "]");
//					}
//				}
//			}
//		}
//		return convXml;
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] bytes = xml.getBytes();
		for (byte b : bytes) {
			boolean add = true;
			for (byte i = 0x00; i <= 0x1f; i++) {
				if (i != 0x0A && i != 0x0D) {
					if (b == i) {
						add = false;
					}
				}
			}
			if (add) {
				out.write(b);
			}
		}
		return new String(out.toByteArray());
	}
	
	
	

	
}
