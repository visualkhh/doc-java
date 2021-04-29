package test.java;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Java8StreamOrOptionalTest {

    @Test
    public void filter() {
        List<String> strings = List.of("A-1", "B-1", "C-1", "A-2");
        List<String> streamResult = strings.stream().filter(it -> it.startsWith("A")).collect(Collectors.toList());
        log.info("streamResult: {}", streamResult);

        List<String> forResult = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            String it = strings.get(i);
            if (it.startsWith("A")) {
                forResult.add(it);
            }
        }
        log.info("forResult: {}", forResult);
    }

    @Test
    public void groupby() {
        List<String> strings = List.of("A-1", "B-1", "C-1", "A-2");
        Map<String, List<String>> streamResult = strings.stream().collect(Collectors.groupingBy(it -> it.split("-")[0]));
        log.info("streamResult: {}", streamResult);


        Map<String, List<String>> forResult = new HashMap<>();
        for (int i = 0; i < strings.size(); i++) {
            String it = strings.get(i);
            String key = it.split("-")[0];
            List<String> value = forResult.get(key);
            if (null == value) {
                value = new ArrayList<>();
            }
            value.add(it);
        }
        log.info("forResult: {}", streamResult);
    }

    @Test
    public void map() {
        List<String> strings = List.of("A-1", "B-1", "C-1", "A-2");
        List<String> streamResult = strings.stream().map(it -> it + "-tail").collect(Collectors.toList());
        log.info("streamResult: {}", streamResult);


        List<String> forResult = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            String it = strings.get(i);
            forResult.add(it + "-tail");
        }
        log.info("forResult: {}", streamResult);
    }

    @Test
    public void set() {
        List<String> strings = List.of("A-1", "A-1", "A-1", "B-2");
        Set<String> streamResult = strings.stream().collect(Collectors.toSet());
        log.info("streamResult: {}", streamResult);


        Set<String> forResult = new HashSet<>();
        for (int i = 0; i < strings.size(); i++) {
            String it = strings.get(i);
            forResult.add(it);
        }
        log.info("forResult: {}", streamResult);
    }

    @Test
    public void flatMap() {
        List<List<String>> strings = List.of(
                List.of("A-1", "A-2", "A-3"),
                List.of("B-1", "B-2", "B-3"),
                List.of("C-1")
        );
        List<String> streamResult = strings.stream().flatMap(it -> it.stream()).collect(Collectors.toList());
        log.info("streamResult: {}", streamResult);


        List<String> forResult = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            List<String> it = strings.get(i);
            for (int j = 0; j < it.size(); j++) {
                forResult.add(it.get(j));
            }
        }
        log.info("forResult: {}", streamResult);
    }

    @Test
    public void combine() {
        // {그룹명}-{번호} 를  그룹명으로 그룹핑하고,  그룹원소에는 값뒤에 ok를 붙여라(A-12-ok) 또한  겹치는 번호가 있으면 중복제거 하여라.
        // 번호중 12는 포함시키지 않는다.

        List<List<String>> strings = List.of(
                List.of("A-1", "A-2", "A-3", "A-2"),
                List.of("B-1", "B-2", "B-3", "B-1"),
                List.of("C-1", "C-2", "C-12")
        );

        Map<String, List<String>> result = strings.stream()
                .flatMap(it -> it.stream())
                .filter(it -> !it.endsWith("12"))
                .map(it -> it + "-ok")
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.groupingBy(it -> it.split("-")[0]));
        log.info("result: {}", result);
    }

    /*
  Map<String, List<NFProfile>> groups = Optional.ofNullable(searchResult.getNfInstances()).orElse(Collections.emptyList()).stream()
                .filter(it -> NFStatus.REGISTERED == it.getNfStatus() && NFType.AMF == it.getNfType())
                .filter(it -> null != it.getAmfInfo() && Strings.isNotBlank(it.getAmfInfo().getAmfSetId()) && Strings.isNotBlank(it.getAmfInfo().getAmfRegionId()))
                .collect(Collectors.groupingBy(it -> it.getAmfInfo().getAmfSetId() + "_" + it.getAmfInfo().getAmfRegionId()));

        List<NFProfile> merged = new ArrayList<>();
        groups.values().stream().filter(it -> CollectionUtils.isNotEmpty(it)).forEach(it -> {

            NFProfile pick = it.get(0);

            // nfService merge
            List<NFService> mergeService = it.stream().flatMap(sit -> sit.getNfServices().stream()).collect(Collectors.toList());
            pick.setNfServices(mergeService);

            // taiRange merge
            List<TaiRange> mergeTaiRange = it.stream().filter(sit -> CollectionUtils.isNotEmpty(sit.getAmfInfo().getTaiRangeList())).flatMap(sit -> sit.getAmfInfo().getTaiRangeList().stream()).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(mergeTaiRange)) {
                TaiRange taiRanges = mergeTaiRange.get(0);
                List<TacRange> tacRanges = new ArrayList<>();
                // tacRange merge
                Map<String, List<TacRange>> tacGroups = mergeTaiRange.stream().filter(tit -> CollectionUtils.isNotEmpty(tit.getTacRangeList())).flatMap(tit -> tit.getTacRangeList().stream())
                        .collect(Collectors.groupingBy(tit -> tit.getStart() + "_" + tit.getEnd()));
                tacGroups.values().stream().filter(tacGroup -> CollectionUtils.isNotEmpty(tacGroup)).forEach(tac -> {
                    tacRanges.add(tac.get(0));
                });
                taiRanges.setTacRangeList(tacRanges);
                pick.getAmfInfo().setTaiRangeList(List.of(taiRanges));
            }

            merged.add(pick);
        });
        merged.stream().forEach(it -> mergeAmfInfo(it, applyAmfInfo, applyTaiRange));
     */
}
