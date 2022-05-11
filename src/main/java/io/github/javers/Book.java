package io.github.javers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javers.core.metamodel.annotation.TypeName;

@TypeName("Book")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Book {
    private String title;
    private String author;
    private int pages;
    private String isbn;

}
