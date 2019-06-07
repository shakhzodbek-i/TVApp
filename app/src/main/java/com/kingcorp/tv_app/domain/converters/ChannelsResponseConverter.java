package com.kingcorp.tv_app.domain.converters;

import com.kingcorp.tv_app.domain.entity.ChannelEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChannelsResponseConverter extends AbstractConverter<String, List<ChannelEntity>> {

    private static final String IMG_URL_REGEX = "(?:^|[\\W])((ht|f)tp(s?)://|www\\.)" +
            "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+/?)*" +
            "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]*$~@!:/{};']*\\.jpg)";

    private static final String TV_NAME_REGEX = "(tvg-name=\".*?\")";

    private static final String TV_URL_REGEX = "(?:^|[\\W])((ht|f)tp(s?)://|www\\.)" +
            "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+/?)*" +
            "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]*$~@!:/{};']*\\?denms\\.ru)";

    @Override
    public List<ChannelEntity> convert(String bean) {

        List<String> imgUrls = stringMatcher(bean, IMG_URL_REGEX);
        List<String> tvNames = stringMatcher(bean, TV_NAME_REGEX);
        List<String> tvUrls = stringMatcher(bean, TV_URL_REGEX);

        List<ChannelEntity> channels;
        return null;
    }

    @Override
    public String reverse(List<ChannelEntity> entity) {
        return null;
    }




    private List<String> stringMatcher(String txt, String regex) {
        ArrayList<String> foundList = new ArrayList<>();
        final Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

        Matcher matcher = pattern.matcher(txt);
        while (matcher.find()) {
            int matchStart = matcher.start(1);
            int matchEnd = matcher.end();
            foundList.add(txt.substring(matchStart, matchEnd));
        }

        return foundList;
    }
}
