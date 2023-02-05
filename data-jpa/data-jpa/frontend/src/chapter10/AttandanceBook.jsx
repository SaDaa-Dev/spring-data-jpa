import React from 'react';

const students = [
    {
        id:1,
        name:"정"
    },
    {
        id:2,
        name:"병"
    },
    {
        id:3,
        name:"찬"
    },
    {
        id:4,
        name:"짱"
    },
    {
        id:5,
        name:"짱"
    },
]

function AttandanceBook(props) {
    return (
        <ul>
            {students.map((student) => {
                return <li key={student.id}>{student.name}</li>

            })}
        </ul>
    );
}

export default AttandanceBook;
