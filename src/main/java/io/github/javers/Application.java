package io.github.javers;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.MappingStyle;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.diff.ListCompareAlgorithm;
import org.javers.core.diff.changetype.PropertyChange;
import org.javers.core.diff.changetype.PropertyChangeType;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.metamodel.annotation.TypeName;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
public class Application {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        Javers javers = JaversBuilder.javers()
//                .withListCompareAlgorithm(ListCompareAlgorithm.LEVENSHTEIN_DISTANCE)
                .build();

        Book book1 = Book.builder()
                .title("Lore of Running")
                .author("Tim Nokes")
                .pages(930)
                .isbn("0-87322-959-2")
                .build();

        Book book2 = Book.builder()
                .title("Lore of Running")
                .author("Tim Nokes")
                .pages(1000) // changed
                .isbn("0-87322-959-2")
                .build();
//        Book book2 = new Book();
//        BeanUtils.copyProperties(book1, book2);
//        Book book2 = (Book) BeanUtils.cloneBean(book1);
//        book2.setPages(1009);
//        book2.setIsbn("0-87321-959-2");

        final Diff diff = javers.compare(book1, book2);

        final List<ValueChange> changes = diff.getChanges().getChangesByType(ValueChange.class);
        log.info("Changes: {}", changes.size());
        log.info("{}", diff.prettyPrint());
        log.info("Json: \n{}", javers.getJsonConverter().toJson(diff));

        changes.forEach(valueChange -> {
            if(valueChange.isPropertyValueChanged()) {
                log.debug("{} -> {}", valueChange.getLeft(), valueChange.getRight());
            }
        });

    }


}
