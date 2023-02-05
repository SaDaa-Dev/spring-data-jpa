import React from 'react';
import Card from "./Card";

function ProfileCard(props) {
    return (
        <Card title="Inje Lee" backgroundColor="#4ea04e">
            <p>안녕하세요 찬입니다.</p>
            <p>리액트 개발 연습중입니다.</p>
        </Card>
    );
}

export default ProfileCard;