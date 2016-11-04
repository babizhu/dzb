package org.bbz.dzb.consts;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liulaoye on 16-10-31.
 * 错误代码
 */
public enum ErrorCode{
    LOGIN_ERROR( 100 ), NOT_LOGIN( 101 ), OPERATION_NOT_FOUND( 150 );

    private final int number;

    private static final Map<Integer, ErrorCode> numToEnum = new HashMap<>();

    static{
        for( ErrorCode t : values() ) {

            ErrorCode s = numToEnum.put( t.number, t );
            if( s != null ) {
                throw new RuntimeException( t.number + "重复了" );
            }
        }
    }

    ErrorCode( int number ){
        this.number = number;
    }

    public int toNum(){
        return number;
    }

    public static ErrorCode fromNum( int n ){
        return numToEnum.get( n );
    }
}
