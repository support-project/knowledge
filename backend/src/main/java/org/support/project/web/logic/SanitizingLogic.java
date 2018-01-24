package org.support.project.web.logic;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.invoke.MethodHandles;
import java.util.regex.Pattern;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.owasp.html.Handler;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.HtmlSanitizer;
import org.owasp.html.HtmlStreamEventReceiver;
import org.owasp.html.HtmlStreamRenderer;
import org.owasp.html.PolicyFactory;
import org.support.project.common.exception.ParseException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import com.google.common.base.Predicate;

@DI(instance = Instance.Singleton)
public class SanitizingLogic {
    /** ログ */
    private static final Log LOG = LogFactory.getLog(MethodHandles.lookup());

    public static SanitizingLogic get() {
        return Container.getComp(SanitizingLogic.class);
    }

    // The 16 colors defined by the HTML Spec (also used by the CSS Spec)
    private static final Pattern COLOR_NAME = Pattern
            .compile("(?:aqua|black|blue|fuchsia|gray|grey|green|lime|maroon|navy|olive|purple" + "|red|silver|teal|white|yellow)");
    // HTML/CSS Spec allows 3 or 6 digit hex to specify color
    private static final Pattern COLOR_CODE = Pattern.compile("(?:#(?:[0-9a-fA-F]{3}(?:[0-9a-fA-F]{3})?))");
    private static final Pattern NUMBER_OR_PERCENT = Pattern.compile("[0-9]+%?");
    private static final Pattern PARAGRAPH = Pattern.compile("(?:[\\p{L}\\p{N},'\\.\\s\\-_\\(\\)]|&[0-9]{2};)*");
    private static final Pattern HTML_ID = Pattern.compile("[a-zA-Z0-9\\:\\-_\\.]+");
    // force non-empty with a '+' at the end instead of '*'
    private static final Pattern HTML_TITLE = Pattern.compile("[\\p{L}\\p{N}\\s\\-_',:\\[\\]!\\./\\\\\\(\\)&]*");
    private static final Pattern HTML_CLASS = Pattern.compile("[a-zA-Z0-9\\s,\\-_]+");
    private static final Pattern ONSITE_URL = Pattern.compile("(?:[\\p{L}\\p{N}\\\\\\.\\#@\\$%\\+&;\\-_~,\\?=/!]+|\\#(\\w)+)");
    private static final Pattern OFFSITE_URL = Pattern
            .compile("\\s*(?:(?:ht|f)tps?://|file://|smb://|\\\\\\\\|mailto:)[\\p{L}\\p{N}]"
                    + "[\\p{L}\\p{N}\\p{Zs}\\.\\#@\\$%\\+&;:\\-_~,\\?=/!\\(\\)]*+\\s*");
    private static final Pattern NUMBER = Pattern.compile("[+-]?(?:(?:[0-9]+(?:\\.[0-9]*)?)|\\.[0-9]+)");
    private static final Pattern NAME = Pattern.compile("[a-zA-Z0-9\\-_\\$]+");
    private static final Pattern ALIGN = Pattern.compile("(?i)center|left|right|justify|char");
    private static final Pattern VALIGN = Pattern.compile("(?i)baseline|bottom|middle|top");
    private static final Predicate<String> COLOR_NAME_OR_COLOR_CODE = new Predicate<String>() {
        public boolean apply(String s) {
            return COLOR_NAME.matcher(s).matches() || COLOR_CODE.matcher(s).matches();
        }
    };
    private static final Predicate<String> ONSITE_OR_OFFSITE_URL = new Predicate<String>() {
        public boolean apply(String s) {
            boolean result = ONSITE_URL.matcher(s).matches() || OFFSITE_URL.matcher(s).matches();
            if (LOG.isDebugEnabled()) {
                LOG.debug("[ONSITE_OR_OFFSITE_URL]: " + result + "\t" + s);
            }
            return result;
        }
    };
    
    private static final Pattern HISTORY_BACK = Pattern.compile("(?:javascript:)?\\Qhistory.go(-1)\\E");
    private static final Pattern ONE_CHAR = Pattern.compile(".?", Pattern.DOTALL);
    public static final PolicyFactory POLICY_DEFINITION = new HtmlPolicyBuilder()
            .allowElements("a", "label", "noscript", "h1", "h2", "h3", "h4", "h5", "h6", "p", "i", "b", "u", "strong", "em", "small", "big", "pre",
                    "code", "cite", "samp", "sub", "sup", "strike", "center", "blockquote", "hr", "br", "col", "font", "map", "span", "div", "img",
                    "ul", "ol", "li", "dd", "dt", "dl", "tbody", "thead", "tfoot", "table", "td", "th", "tr", "colgroup", "fieldset", "legend", "del",
                    "var")
            .allowAttributes("id").matching(HTML_ID).globally()
            .allowAttributes("slide").matching(NUMBER).globally()
            .allowAttributes("transition").matching(HTML_CLASS).globally()
            .allowAttributes("centered").matching(HTML_CLASS).globally()
            .allowAttributes("class").matching(HTML_CLASS).globally()
            .allowAttributes("lang").matching(Pattern.compile("[a-zA-Z]{2,20}")).globally()
            .allowAttributes("title").matching(HTML_TITLE).globally()
            .allowStyling()
            .allowAttributes("align").matching(ALIGN).onElements("p")
            .allowAttributes("for").matching(HTML_ID).onElements("label")
            .allowAttributes("color").matching(COLOR_NAME_OR_COLOR_CODE).onElements("font")
            .allowAttributes("face").matching(Pattern.compile("[\\w;, \\-]+")).onElements("font")
            .allowAttributes("size").matching(NUMBER).onElements("font")
            .allowAttributes("href").matching(ONSITE_OR_OFFSITE_URL).onElements("a")
            .allowUrlProtocols("http", "https", "mailto", "file", "smb", "\\\\").allowAttributes("target").onElements("a")
            .requireRelNofollowOnLinks()
            .allowAttributes("src").matching(ONSITE_OR_OFFSITE_URL).onElements("img")
            .allowAttributes("name").matching(NAME).onElements("img")
            .allowAttributes("alt").matching(PARAGRAPH).onElements("img")
            .allowAttributes("border", "hspace", "vspace").matching(NUMBER).onElements("img")
            .allowAttributes("border", "cellpadding", "cellspacing").matching(NUMBER).onElements("table")
            .allowAttributes("bgcolor").matching(COLOR_NAME_OR_COLOR_CODE).onElements("table")
            .allowAttributes("background").matching(ONSITE_URL).onElements("table")
            .allowAttributes("align").matching(ALIGN).onElements("table")
            .allowAttributes("noresize").matching(Pattern.compile("(?i)noresize")).onElements("table")
            .allowAttributes("background").matching(ONSITE_URL).onElements("td", "th", "tr")
            .allowAttributes("bgcolor").matching(COLOR_NAME_OR_COLOR_CODE).onElements("td", "th")
            .allowAttributes("abbr").matching(PARAGRAPH).onElements("td", "th")
            .allowAttributes("axis", "headers").matching(NAME).onElements("td", "th")
            .allowAttributes("scope").matching(Pattern.compile("(?i)(?:row|col)(?:group)?")).onElements("td", "th")
            .allowAttributes("nowrap").onElements("td", "th")
            .allowAttributes("height", "width").matching(NUMBER_OR_PERCENT).onElements("table", "td", "th", "tr", "img")
            .allowAttributes("align").matching(ALIGN).onElements("thead", "tbody", "tfoot", "img", "td", "th", "tr", "colgroup", "col")
            .allowAttributes("valign").matching(VALIGN).onElements("thead", "tbody", "tfoot", "td", "th", "tr", "colgroup", "col")
            .allowAttributes("charoff").matching(NUMBER_OR_PERCENT).onElements("td", "th", "tr", "colgroup", "col", "thead", "tbody", "tfoot")
            .allowAttributes("char").matching(ONE_CHAR).onElements("td", "th", "tr", "colgroup", "col", "thead", "tbody", "tfoot")
            .allowAttributes("colspan", "rowspan").matching(NUMBER).onElements("td", "th")
            .allowAttributes("span", "width").matching(NUMBER_OR_PERCENT).onElements("colgroup", "col")
            .toFactory();

    private Transformer transformer = null;

    private void setUpTransformer() throws TransformerConfigurationException, TransformerFactoryConfigurationError {
        if (transformer == null) {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "html");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            // transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, String.valueOf(2));
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(2));
        }
    }

    public String indent(String html) throws TransformerFactoryConfigurationError, TransformerException, IOException {
        setUpTransformer();
        StringBuilder builder = new StringBuilder();
        builder.append("<root>").append(html).append("</root>");
        byte[] bytes = builder.toString().getBytes();

        InputStream is = null;
        OutputStream os = null;
        try {
            is = new ByteArrayInputStream(bytes);
            StreamSource src = new StreamSource(is);
            os = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(os);
            transformer.transform(src, result);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new StringReader(os.toString()));
                builder = new StringBuilder();
                String s;
                while ((s = reader.readLine()) != null) {
                    if (!s.equals("<root>") && !s.equals("</root>")) {
                        builder.append(s);
                        builder.append("\n");
                    }
                }
                return builder.toString();
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }

    public String sanitize(String untrustedHTML) throws ParseException {
        return sanitize(untrustedHTML, false);
    }

    
    public String sanitize(String untrustedHTML, boolean clean) throws ParseException {
        StringBuilder buffer = new StringBuilder();
        final HtmlStreamEventReceiver renderer = HtmlStreamRenderer.create(buffer, new Handler<IOException>() {
            public void handle(IOException ex) {
                // OPEN ITEM: Some other exception type more appropriate here?
                LOG.error("Error creating AntiSamy policy for HTML Sanitizer", ex);
            }
        }, new Handler<String>() {
            public void handle(String errorMessage) {
                LOG.error(errorMessage);
                // OPEN ITEM: Should we also throw something here??? If so what?
            }
        });
        HtmlSanitizer.Policy policy = POLICY_DEFINITION.apply(renderer);
        HtmlSanitizer.sanitize(untrustedHTML, policy);
        if (clean) {
            try {
                return indent(buffer.toString());
            } catch (Exception e) {
                LOG.error("indent error.\n" + buffer.toString(), e);
                throw new ParseException(e);
            }
        }
        return buffer.toString();
    }

}
