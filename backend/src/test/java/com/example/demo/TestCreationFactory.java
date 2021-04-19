package com.example.demo;

import com.example.demo.bookstore.dto.BookDto;
import com.example.demo.bookstore.dto.BookMinimalDto;
import com.example.demo.bookstore.model.Book;
import com.example.demo.user.dto.UserListDto;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(UserListDto.class)) {
            supplier = TestCreationFactory::newUserListDto;
        } else if (cls.equals(User.class)) {
            supplier = TestCreationFactory::newUser;
        } else if (cls.equals(BookDto.class)) {
            supplier = TestCreationFactory::newBookDto;
        } else if (cls.equals(Book.class)) {
            supplier = TestCreationFactory::newBook;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    public static Book newBook() {
        return Book.builder()
                .id(randomLong())
                .name(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomDouble())
                .quantity(randomInteger())
                .build();
    }

    public static BookMinimalDto newBookMinimalDto() {
        return BookMinimalDto.builder()
                .name(randomString())
                .quantity(randomInteger())
                .author(randomString())
                .genre(randomString())
                .price(randomDouble())
                .build();
    }
    public static BookDto newBookDto() {
        BookDto book = new BookDto();
        book.setId(randomLong());
        book.setName(randomString());
        book.setAuthor(randomString());
        book.setGenre(randomString());
        book.setPrice(randomDouble());
        book.setQuantity(randomInteger());
        return book;
    }

    public static UserListDto newUserListDto() {
        return UserListDto.builder()
                .id(randomLong())
                .name(randomString())
                .email(randomEmail())
                .role("REGULAR")
                .build();
    }

    public static User newUser() {
        Role role = Role.builder()
                .name(ERole.REGULAR)
                .id(1L)
                .build();

        return User.builder()
                .id(randomLong())
                .email(randomEmail())
                .role(role)
                .password(randomString())
                .username(randomString())
                .build();
    }
    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomLong())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static long randomLong() {
        return new Random().nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static Double randomDouble() {
        return new Random().nextDouble();
    }

    public static Integer randomInteger() {
        return new Random().nextInt();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
