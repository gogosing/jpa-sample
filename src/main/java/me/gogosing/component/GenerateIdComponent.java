package me.gogosing.component;

/**
 * 레코드 대체 식별자 생성 Component.
 */
public interface GenerateIdComponent {

    /**
     * 앨범 레코드 대체 식별자 생성.
     * @return 생성된 앨범 레코드 대체 식별자.
     */
    String albumId();

    /**
     * 음원 레코드 대체 식별자 생성.
     * @return 생성된 음원 레코드 대체 식별자.
     */
    String songId();
}
